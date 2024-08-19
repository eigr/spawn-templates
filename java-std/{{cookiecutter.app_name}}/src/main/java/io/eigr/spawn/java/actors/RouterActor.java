package io.eigr.spawn.java.actors;

import io.eigr.spawn.api.ActorIdentity;
import io.eigr.spawn.api.ActorRef;
import io.eigr.spawn.api.Spawn;
import io.eigr.spawn.api.actors.ActorContext;
import io.eigr.spawn.api.actors.StatelessActor;
import io.eigr.spawn.api.actors.Value;
import io.eigr.spawn.api.actors.behaviors.ActorBehavior;
import io.eigr.spawn.api.actors.behaviors.BehaviorCtx;
import io.eigr.spawn.api.actors.behaviors.NamedActorBehavior;
import io.eigr.spawn.api.actors.workflows.Forward;
import io.eigr.spawn.api.exceptions.ActorCreationException;
import io.eigr.spawn.internal.ActionBindings;

import io.eigr.spawn.java.domain.Common;
import io.eigr.spawn.java.service.PostalCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.action;
import static io.eigr.spawn.api.actors.behaviors.ActorBehavior.name;

public class RouterActor implements StatelessActor {
    private static final Logger log = LoggerFactory.getLogger(RouterActor.class);

    private PostalCodeService postalCodeService;

    @Override
    public ActorBehavior configure(BehaviorCtx behaviorCtx) {
        this.postalCodeService = behaviorCtx.getInjector().getInstance(PostalCodeService.class);
        return new NamedActorBehavior(
                name("Router"),
                action("GetPostalCode", ActionBindings.of(Common.GetRequest.class, this::getPostalCodeData))
        );
    }

    private Value getPostalCodeData(ActorContext<?> context, Common.GetRequest msg) {
        log.debug("Received invocation. Message: '{}'.", msg, context);
        try {
            Spawn spawn = context.getSpawnSystem();
            ActorRef actorRef = context.getSpawnSystem()
                    .createActorRef(ActorIdentity.of(spawn.getSystem(), msg.getCode(), "PostalCode"));

            return Value.at()
                    .flow(Forward.to(actorRef, "GetPostalCodeData"))
                    .reply();

        } catch (ActorCreationException e) {
            throw new RuntimeException(e);
        }
    }
}
