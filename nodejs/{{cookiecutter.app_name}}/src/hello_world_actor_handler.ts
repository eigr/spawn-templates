import { ActorContext, Value } from '@eigr/spawn-sdk'
import {
  HelloWorldActorState,
  SayHelloResponse,
  SayHelloRequest
} from './src/generated/actors/hello'

export const sayHelloHandler = async (
  context: ActorContext<HelloWorldActorState>,
  payload: SayHelloRequest
): Promise<Value> => {
  const times = context.state.times + 1 || 1

  const newState: HelloWorldActorState = { times, lastMessage: payload.message }
  const response: SayHelloResponse = {
    response: `Hello, ${payload.message}! This was executed: ${times} times`
  }

  return Value.of<HelloWorldActorState, SayHelloResponse>().state(newState).response(response)
}
