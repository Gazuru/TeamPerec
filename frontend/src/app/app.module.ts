import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BoardAdminComponent} from './components/board-admin/board-admin.component';
import {BoardModeratorComponent} from './components/board-moderator/board-moderator.component';
import {BoardUserComponent} from './components/board-user/board-user.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {ProfileComponent} from './components/profile/profile.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import { CaffsComponent } from './components/caffs/caffs.component';
import { MyCaffsComponent } from './components/my-caffs/my-caffs.component';
import { UploadCaffComponent } from './components/upload-caff/upload-caff.component';
import { DetailsCaffComponent } from './components/details-caff/details-caff.component';
import { DetailsProfileComponent } from './components/details-profile/details-profile.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { CaffCardComponent } from './components/caff-card/caff-card.component';

@NgModule({
  declarations: [
    AppComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    CaffsComponent,
    MyCaffsComponent,
    UploadCaffComponent,
    DetailsCaffComponent,
    DetailsProfileComponent,
    EditProfileComponent,
    CaffCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
