defmodule {{{cookiecutter.module_app_name}}.MixProject do
  use Mix.Project

  def project do
    [
      app: :{{cookiecutter.app_name}},
      version: "0.1.0",
      elixir: "~> 1.14",
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  def application do
    [
      extra_applications: [:logger],
      mod: {{{cookiecutter.module_app_name}}.Application, []}
    ]
  end

  defp deps do
    [
      {:spawn_sdk, "~> {{cookiecutter.spawn_sdk_version}}"},
      # You can uncomment one of those dependencies if you are going to use Persistent Actors
      #{:spawn_statestores_mariadb, "~> {{cookiecutter.spawn_sdk_version}}"},
      #{:spawn_statestores_mysql, "~> {{cookiecutter.spawn_sdk_version}}"},
      #{:spawn_statestores_postgres, "~> {{cookiecutter.spawn_sdk_version}}"},
      #{:spawn_statestores_mssql, "~> {{cookiecutter.spawn_sdk_version}}"},
      #{:spawn_statestores_cockroachdb, "~> {{cookiecutter.spawn_sdk_version}}"},
      #{:spawn_statestores_sqlite, "~> {{cookiecutter.spawn_sdk_version}}"}
    ]
  end
end
