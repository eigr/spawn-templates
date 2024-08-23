package io.eigr.spawn.java.actors;

import domain.actors.GetRequest;
import domain.actors.GetResponse;
import domain.actors.PostalCodeState;
import domain.actors.PostalCodeStatus;
import io.eigr.spawn.api.actors.ActorContext;
import io.eigr.spawn.api.actors.StatefulActor;
import io.eigr.spawn.api.actors.Value;
import io.eigr.spawn.api.actors.behaviors.ActorBehavior;
import io.eigr.spawn.api.actors.behaviors.BehaviorCtx;
import io.eigr.spawn.api.actors.behaviors.UnNamedActorBehavior;
import io.eigr.spawn.internal.ActionBindings;
import io.eigr.spawn.java.service.PostalCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.action;
import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.name;

public final class PostalCodeActor implements StatefulActor<PostalCodeState> {
    private static final Logger log = LoggerFactory.getLogger(PostalCodeActor.class);

    private PostalCodeService postalCodeService;

    @Override
    public ActorBehavior configure(BehaviorCtx behaviorCtx) {
        this.postalCodeService = behaviorCtx.getInjector().getInstance(PostalCodeService.class);
        return new UnNamedActorBehavior(
                name("PostalCode"),
                action("GetPostalCodeData", ActionBindings.of(GetRequest.class, this::getPostalCodeData)));
    }

    private Value getPostalCodeData(ActorContext<PostalCodeState> context, GetRequest msg) {
        log.debug("Received invocation. Message: '{}'. Context: '{}'.", msg, context);

        Optional<PostalCodeState> currentState = context.getState();

        // Return current state if available and not unknown
        if (currentState.isPresent() && currentState.get().getStatus() != PostalCodeStatus.UNKNOWN) {
            return createValueWithStateAndResponse(currentState.get());
        }

        log.trace("State not present.");
        Map<String, String> postalCodeData = postalCodeService.find(msg.getCode());

        // If postal code data found, build state and response
        if (!postalCodeData.isEmpty()) {
            PostalCodeState newState = buildPostalCodeState(msg.getCode(), postalCodeData,
                    PostalCodeStatus.FOUND);
            return createValueWithStateAndResponse(newState);
        }

        // Default case: return unknown state
        PostalCodeState unknownState = PostalCodeState.newBuilder()
                .setStatus(PostalCodeStatus.UNKNOWN)
                .build();
        return createValueWithStateAndResponse(unknownState);
    }

    private Value createValueWithStateAndResponse(PostalCodeState state) {
        GetResponse response = GetResponse.newBuilder()
                .setPostalCode(state)
                .build();
        return Value.at().state(state).response(response).reply();
    }

    private PostalCodeState buildPostalCodeState(String code, Map<String, String> data,
            PostalCodeStatus status) {
        return PostalCodeState.newBuilder()
                .setCode(code)
                .setCity(data.get("localidade"))
                .setState(data.get("uf"))
                .setStreet(data.get("logradouro"))
                .setCountry(data.get("pais"))
                .setStatus(status)
                .build();
    }

}
