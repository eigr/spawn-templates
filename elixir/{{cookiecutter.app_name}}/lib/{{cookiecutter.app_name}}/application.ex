defmodule {{cookiecutter.app_module_name}}.Application do
  @moduledoc false
  use Application

  @impl true
  def start(_type, _args) do
    children = [
      {
        SpawnSdk.System.Supervisor,
        system: "{{cookiecutter.spawn_app_spawm_system}}",
        actors: [
          {{cookiecutter.app_module_name}}.Actors.JoeActor
        ]
      }
    ]

    opts = [strategy: :one_for_one, name: {{cookiecutter.app_module_name}}.Supervisor]
    Supervisor.start_link(children, opts)
  end
end
