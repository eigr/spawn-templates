defmodule {{cookiecutter.app_module_name}}.MixProject do
  use Mix.Project

  def project do
    [
      app: :{{cookiecutter.app_name}},
      version: "0.1.0",
      elixir: "~> {{{cookiecutter.elixir_version}}",
      start_permanent: Mix.env() == :prod,
      deps: deps(),
      releases: releases()
    ]
  end

  def application do
    [
      extra_applications: [:logger],
      mod: {{{cookiecutter.app_module_name}}.Application, []}
    ]
  end

  defp deps do
    [
      {:bakeware, "~> 0.2"},
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

  defp releases do
    [
      {{cookiecutter.app_name}}: [
        include_executables_for: [:unix],
        applications: [{{cookiecutter.app_name}}: :permanent],
        steps: [
          :assemble,
          &Bakeware.assemble/1
        ],
        bakeware: [compression_level: 19]
      ]
    ]
  end
end
