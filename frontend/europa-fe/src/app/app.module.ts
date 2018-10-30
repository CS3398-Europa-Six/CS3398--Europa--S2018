import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { HomeComponent } from './home/home.component';
import { CarsComponent } from './dash/cars/cars.component';
import { ChangelogComponent } from './changelog/changelog.component';
import { FooterComponent } from './footer/footer.component';
import { DashComponent } from './dash/dash.component';
import { OverviewComponent } from './dash/overview/overview.component';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { SimplesearchComponent } from './simplesearch/simplesearch.component';
import { AdvancedSearchComponent } from './advanced-search/advanced-search.component';
import { ContactComponent } from './contact/contact.component';
import { AddCarComponent } from './dash/add-car/add-car.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CarsComponent,
    ChangelogComponent,
    FooterComponent,
    DashComponent,
    OverviewComponent,
    SignupComponent,
    LoginComponent,
    SimplesearchComponent,
    AdvancedSearchComponent,
    ContactComponent,
    AddCarComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
