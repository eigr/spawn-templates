package io.eigr.spawn.java.actors;

import io.eigr.spawn.api.actors.ActorContext;
import io.eigr.spawn.api.actors.StatefulActor;
import io.eigr.spawn.api.actors.Value;
import io.eigr.spawn.api.actors.behaviors.ActorBehavior;
import io.eigr.spawn.api.actors.behaviors.BehaviorCtx;
import io.eigr.spawn.api.actors.behaviors.UnNamedActorBehavior;
import io.eigr.spawn.internal.ActionBindings;

import io.eigr.spawn.java.domain.DomainProto;
import io.eigr.spawn.java.service.PostalCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.action;
import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.name;

public class PostalCodeActor implements StatefulActor<DomainProto.PostalCodeState> {
    private static final Logger log = LoggerFactory.getLogger(PostalCodeActor.class);

    private PostalCodeService postalCodeService;

    @Override
    public ActorBehavior configure(BehaviorCtx behaviorCtx) {
        this.postalCodeService = behaviorCtx.getInjector().getInstance(PostalCodeService.class);
        return new UnNamedActorBehavior(
                name("PostalCode"),
                action("GetPostalCodeData", ActionBindings.of(DomainProto.GetRequest.class, this::getPostalCodeData))
        );
    }

    private Value getPostalCodeData(ActorContext<DomainProto.PostalCodeState> context, DomainProto.GetRequest msg) {
        log.debug("Received invocation. Message: '{}'. Context: '{}'.", msg, context);

        Optional<DomainProto.PostalCodeState> currentState = context.getState();

        // Return current state if available and not unknown
        if (currentState.isPresent() && currentState.get().getStatus() != DomainProto.PostalCodeStatus.UNKNOWN) {
            return createValueWithStateAndResponse(currentState.get());
        }

        log.trace("State not present.");
        Map<String, String> postalCodeData = postalCodeService.find(msg.getCode());

        // If postal code data found, build state and response
        if (!postalCodeData.isEmpty()) {
            DomainProto.PostalCodeState newState = buildPostalCodeState(msg.getCode(), postalCodeData, DomainProto.PostalCodeStatus.FOUND);
            return createValueWithStateAndResponse(newState);
        }

        // Default case: return unknown state
        DomainProto.PostalCodeState unknownState = DomainProto.PostalCodeState.newBuilder()
                .setStatus(DomainProto.PostalCodeStatus.UNKNOWN)
                .build();
        return createValueWithStateAndResponse(unknownState);
    }

    private Value createValueWithStateAndResponse(DomainProto.PostalCodeState state) {
        DomainProto.GetResponse response = DomainProto.GetResponse.newBuilder()
                .setPostalCode(state)
                .build();
        return Value.at().state(state).response(response).reply();
    }

    private DomainProto.PostalCodeState buildPostalCodeState(String code, Map<String, String> data, DomainProto.PostalCodeStatus status) {
        return DomainProto.PostalCodeState.newBuilder()
                .setCode(code)
                .setCity(data.get("localidade"))
                .setState(data.get("uf"))
                .setStreet(data.get("logradouro"))
                .setCountry(data.get("pais"))
                .setStatus(status)
                .build();
    }

}

