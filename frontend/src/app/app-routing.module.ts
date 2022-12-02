import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {CaffsComponent} from "./components/caffs/caffs.component";
import {MyCaffsComponent} from "./components/my-caffs/my-caffs.component";
import {UploadCaffComponent} from "./components/upload-caff/upload-caff.component";
import {DetailsCaffComponent} from "./components/details-caff/details-caff.component";
import {DetailsProfileComponent} from "./components/details-profile/details-profile.component";
import {AuthUserGuard} from "./guards/auth-user.guard";
import {EditProfileComponent} from "./components/edit-profile/edit-profile.component";
import {ProfilesComponent} from "./components/profiles/profiles.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'caffs', component: CaffsComponent, canActivate: [AuthUserGuard]},
  {path: 'my-caffs', component: MyCaffsComponent},
  {path: 'upload-caff', component: UploadCaffComponent},
  {path: 'details-caff/:id', component: DetailsCaffComponent},
  {path: 'details-profile/:id', component: DetailsProfileComponent},
  {path: 'edit-profile/:id', component: EditProfileComponent},
  {path: 'profiles', component: ProfilesComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
