package io.eigr.spawn.java;

import io.eigr.spawn.api.Spawn;
import io.eigr.spawn.api.TransportOpts;
import io.eigr.spawn.api.exceptions.SpawnException;
import io.eigr.spawn.api.extensions.DependencyInjector;
import io.eigr.spawn.api.extensions.SimpleDependencyInjector;

import io.eigr.spawn.java.actors.PostalCodeActor;
import io.eigr.spawn.java.actors.RouterActor;
import io.eigr.spawn.java.service.PostalCodeService;
import io.eigr.spawn.java.service.ViaCepService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;

public final class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws SpawnException {
        Config cfg = Config.createDefaultConfig();

        DependencyInjector dependencyInjector = SimpleDependencyInjector.createInjector();
        dependencyInjector.bind(PostalCodeService.class, new ViaCepService());

        Spawn spawnSystem = new Spawn.SpawnSystem()
                .create(cfg.spawnSystemName, dependencyInjector)
                .withActor(RouterActor.class)
                .withActor(PostalCodeActor.class)
                .withTerminationGracePeriodSeconds(5)
                .withTransportOptions(
                        TransportOpts.builder()
                                .host(cfg.userFunctionHost)
                                .port(Integer.parseInt(cfg.userFunctionPort))
                                .proxyHost(cfg.spawnProxyHost)
                                .proxyPort(Integer.parseInt(cfg.spawnProxyPort))
                                .build())
                .build();

        spawnSystem.start();

        log.info("Actor running and ready to accept connections at port [{}]", cfg.userFunctionPort);
    }

    public record Config(String startupDelaySeconds,
            String userFunctionHost,
            String userFunctionPort,
            String spawnProxyHost,
            String spawnProxyPort,
            String spawnSystemName) {

        public static Config createDefaultConfig() {
            String startupDelaySeconds = System.getenv("STARTUP_DELAY_SECONDS") != null
                    ? System.getenv("STARTUP_DELAY_SECONDS")
                    : "10";
            String userFunctionHost = System.getenv("USER_FUNCTION_HOST") != null ? System.getenv("USER_FUNCTION_HOST")
                    : "localhost";
            String userFunctionPort = System.getenv("USER_FUNCTION_PORT") != null ? System.getenv("USER_FUNCTION_PORT")
                    : "8090";
            String spawnProxyHost = System.getenv("SPAWN_PROXY_HOST") != null ? System.getenv("SPAWN_PROXY_HOST")
                    : "localhost";
            String spawnProxyPort = System.getenv("SPAWN_PROXY_PORT") != null ? System.getenv("SPAWN_PROXY_PORT")
                    : "9001";
            String spawnSystemName = System.getenv("SPAWN_SYSTEM_NAME") != null ? System.getenv("SPAWN_SYSTEM_NAME")
                    : "spawn-system";

            return new Config(startupDelaySeconds, userFunctionHost, userFunctionPort, spawnProxyHost,
                    spawnProxyPort, spawnSystemName);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Config.class.getSimpleName() + "[", "]")
                    .add("DELAY_SECONDS='" + startupDelaySeconds + "'")
                    .add("USER_FUNCTION_HOST='" + userFunctionHost + "'")
                    .add("USER_FUNCTION_PORT='" + userFunctionPort + "'")
                    .add("SPAWN_PROXY_HOST='" + spawnProxyHost + "'")
                    .add("SPAWN_PROXY_PORT='" + spawnProxyPort + "'")
                    .add("SPAWN_SYSTEM_NAME='" + spawnSystemName + "'")
                    .toString();
        }
    }
}

