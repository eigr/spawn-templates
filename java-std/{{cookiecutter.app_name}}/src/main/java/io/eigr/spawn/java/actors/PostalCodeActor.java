package io.eigr.spawn.java.actors;

import io.eigr.spawn.api.actors.ActorContext;
import io.eigr.spawn.api.actors.StatefulActor;
import io.eigr.spawn.api.actors.Value;
import io.eigr.spawn.api.actors.behaviors.ActorBehavior;
import io.eigr.spawn.api.actors.behaviors.BehaviorCtx;
import io.eigr.spawn.api.actors.behaviors.UnNamedActorBehavior;
import io.eigr.spawn.internal.ActionBindings;

import io.eigr.spawn.java.domain.Common;
import io.eigr.spawn.java.service.PostalCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.action;
import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.name;

public class PostalCodeActor implements StatefulActor<Common.PostalCodeState> {
    private static final Logger log = LoggerFactory.getLogger(PostalCodeActor.class);

    private PostalCodeService postalCodeService;

    @Override
    public ActorBehavior configure(BehaviorCtx behaviorCtx) {
        this.postalCodeService = behaviorCtx.getInjector().getInstance(PostalCodeService.class);
        return new UnNamedActorBehavior(
                name("PostalCode"),
                action("GetPostalCodeData", ActionBindings.of(Common.GetRequest.class, this::getPostalCodeData))
        );
    }

    private Value getPostalCodeData(ActorContext<Common.PostalCodeState> context, Common.GetRequest msg) {
        log.debug("Received invocation. Message: '{}'. Context: '{}'.", msg, context);
        Common.PostalCodeState.Builder builder = Common.PostalCodeState.newBuilder();

        if (context.getState().isPresent() && !Common.PostalCodeStatus.UNKNOWN.equals(state.getStatus())) 
            return Value.at()
                .state(state)
                .response(Common.GetResponse.newBuilder().setPostalCode(state).build())
                .reply();
        }

        log.trace("State not present.");
        Map<String, String> postalCode = postalCodeService.find(msg.getCode());
        if(!postalCode.isEmpty()) {
            Common.PostalCodeState state = builder.setCode(msg.getCode())
                    .setCity(postalCode.get("localidade"))
                    .setState(postalCode.get("uf"))
                    .setStreet(postalCode.get("logradouro"))
                    .setCountry(postalCode.get("pais"))
                    .setStatus(Common.PostalCodeStatus.FOUND)
                    .build();

            Common.GetResponse response = Common.GetResponse.newBuilder()
                    .setPostalCode(state)
                    .build();

            return Value.at()
                    .state(state)
                    .response(response)
                    .reply();
        }

        return Value.at()
                .state(builder
                        .setStatus(Common.PostalCodeStatus.UNKNOWN)
                        .build())
                .response(Common.GetResponse.newBuilder().build())
                .reply();
    }
}

