import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {BoardUserComponent} from "./components/board-user/board-user.component";
import {BoardModeratorComponent} from "./components/board-moderator/board-moderator.component";
import {BoardAdminComponent} from "./components/board-admin/board-admin.component";
import {CaffsComponent} from "./components/caffs/caffs.component";
import {MyCaffsComponent} from "./components/my-caffs/my-caffs.component";
import {UploadCaffComponent} from "./components/upload-caff/upload-caff.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'caffs', component: CaffsComponent },
  { path: 'my-caffs', component: MyCaffsComponent },
  { path: 'upload-caff', component: UploadCaffComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
