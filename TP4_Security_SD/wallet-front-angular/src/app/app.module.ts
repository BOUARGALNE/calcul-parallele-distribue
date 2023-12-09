import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GraphQLModule } from './graphql.module';
import { HttpClientModule } from '@angular/common/http';
import {NavbarComponent} from "./navbar/navbar.component";
import {CurrenciesComponent} from "./currencies/currencies.component";
import {ContinentsComponent} from "./continents/continents.component";
import {WalletsComponent} from "./wallets/wallets.component";
import {WalletTransactionsComponent} from "./wallet-transactions/wallet-transactions.component";
import {CurrencyDepositComponent} from "./currency-deposit/currency-deposit.component";
import {ReactiveFormsModule} from "@angular/forms";
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

export function kcFactory(kcService: KeycloakService) {
  return () => kcService.init({
    config: {
      url: 'http://localhost:8080',
      realm: 'wallet-realm',
      clientId: 'wallet-realm-bouargalne'
    },
    initOptions: {
      onLoad: 'login-required',
      checkLoginIframe: false
    }
  });
}
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CurrenciesComponent,
    ContinentsComponent,
    WalletsComponent,
    WalletTransactionsComponent,
    CurrencyDepositComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GraphQLModule,
    HttpClientModule,
    ReactiveFormsModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      deps: [KeycloakService],
      multi: true,
      useFactory: kcFactory
      
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
