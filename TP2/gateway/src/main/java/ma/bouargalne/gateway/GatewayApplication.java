package ma.bouargalne.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    //=>Méthode statique: je connais les routes
    //@Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder){ //configurer les rootes
        return builder.routes()
//                .route(r->r.path("/customers/**").uri("http://localhost:8081/"))
//                .route(r->r.path("/products/**").uri("http://localhost:8082/"))
                .route(r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
                .route(r->r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
                .build();

    }

    //=>Méthode dynamique:
    //  - je ne connais pas les route
    //  - à chaque fois que tu reçoit une requete, regarde dans l'URL de la requete tu vas trouvé le nom du Micro Service
    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(
            ReactiveDiscoveryClient rdc,
            DiscoveryLocatorProperties dlp){

        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);

    }


}
