defmodule {{cookiecutter.app_module_name}}.Actors.JoeActor do
  @moduledoc false
  use SpawnSdk.Actor,
    name: "Joe",
    state_type: Io.Eigr.Spawn.Example.MyState

  require Logger

  alias Io.Eigr.Spawn.Example.{
    MyState,
    MyBusinessMessage
  }

  init(fn %Context{state: state} = ctx ->
    Logger.info("[joe] Received InitRequest. Context: #{inspect(ctx)}")

    new_state =
      if is_nil(state) do
        %MyState{value: 0}
      else
        state
      end

    %Value{}
    |> Value.state(new_state)
    |> Value.reply!()
  end)

  action("Sum", fn %Context{state: state} = ctx, %MyBusinessMessage{value: value} = data ->
    Logger.info("[joe] Received Request: #{inspect(data)}. Context: #{inspect(ctx)}")

    new_value =
      if is_nil(state) do
        0 + value
      else
        (state.value || 0) + value
      end

    response = %MyBusinessMessage{value: new_value}

    %Value{}
    |> Value.of(response, %MyState{value: new_value})
    |> Value.reply!()
  end)
end
