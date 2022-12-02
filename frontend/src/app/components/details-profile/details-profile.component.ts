import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../../services/profile.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {ProfileDownloadResponse} from "../../models/profile-download-response";
import {ProfileCaffResponse} from "../../models/profile-caff-response";
import {ProfileResponse} from "../../models/profile-response";

@Component({
  selector: 'app-details-profile',
  templateUrl: './details-profile.component.html',
  styleUrls: ['./details-profile.component.css']
})
export class DetailsProfileComponent implements OnInit {
  profile: ProfileResponse = {
    name: "",
    email: "",
    downloads:[],
    caffs: []
  };
  caffs: ProfileCaffResponse[] = [];
  downloads: ProfileDownloadResponse[] = []

  //downloads:DownloadResponse[]=[];
  id: string = "";
  showDeleteButton = false;


  private sub: any;

  constructor(private route: ActivatedRoute, private profileService: ProfileService, private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.profileService.getProfile(this.id).subscribe(
        data => {
          console.log(data);
          this.profile = data;
          this.caffs = this.profile.caffs;
          this.downloads = this.profile.downloads;
        }
      );
      this.showDeleteButton = this.tokenStorageService.getUser().roles.includes("ROLE_ADMIN");
    });
  }


  deleteProfile() {
    this.profileService.deleteProfile(this.id);
  }
}
