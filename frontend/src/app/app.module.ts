import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {ProfileComponent} from './components/profile/profile.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import {CaffsComponent} from './components/caffs/caffs.component';
import {MyCaffsComponent} from './components/my-caffs/my-caffs.component';
import {UploadCaffComponent} from './components/upload-caff/upload-caff.component';
import {DetailsCaffComponent} from './components/details-caff/details-caff.component';
import {DetailsProfileComponent} from './components/details-profile/details-profile.component';
import {EditProfileComponent} from './components/edit-profile/edit-profile.component';
import {CaffCardComponent} from './components/caff-card/caff-card.component';
import { CommentComponent } from './components/comment/comment.component';
import { CaffCardMinimalComponent } from './components/caff-card-minimal/caff-card-minimal.component';
import { DownloadedCaffCardComponent } from './components/downloaded-caff-card/downloaded-caff-card.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    CaffsComponent,
    MyCaffsComponent,
    UploadCaffComponent,
    DetailsCaffComponent,
    DetailsProfileComponent,
    EditProfileComponent,
    CaffCardComponent,
    CommentComponent,
    CaffCardMinimalComponent,
    DownloadedCaffCardComponent
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
