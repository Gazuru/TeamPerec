import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../services/profile.service";
import {ProfileResponse} from "../../models/profile-response";
import {TokenStorageService} from "../../services/token-storage.service";
import {EMPTY, Observable} from "rxjs";

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {
  profiles: Observable<ProfileResponse[]> = EMPTY;
  showAdmin = false;

  constructor(private profileService: ProfileService, private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.profiles = this.profileService.getProfiles();
    this.showAdmin = this.tokenStorageService.getUser().roles.includes('ROLE_ADMIN');
  }

  deleteProfile(id: string) {
    this.profileService.deleteProfile(id);
    window.location.reload();
  }
}
