import spawn, { Kind } from '@eigr/spawn-sdk'
import {
  HelloWorldActorState,
  SayHelloRequest,
  SayHelloResponse
} from './src/generated/actors/hello'
import { sayHelloHandler } from './src/hello_world_actor_handler'

const system = spawn.createSystem('{{ cookiecutter.spawn_system }}')

const actor = system.buildActor({
  name: 'HelloWorldActor',
  kind: Kind.NAMED,
  stateType: HelloWorldActorState,
  stateful: true,
  snapshotTimeout: 5_000n,
  deactivatedTimeout: 10_000n
})

actor.addAction(
  { name: 'SayHello', payloadType: SayHelloRequest, responseType: SayHelloResponse },
  sayHelloHandler
)

system.register().then(() => {
  console.log('[SpawnSystem] Actors registered successfully')

  console.debug(
    '[SpawnSystem] [debug] Make sure to run the Spawn Proxy with the `spawnctl dev run` command'
  )
})
