import {ProfileDownloadResponse} from "./profile-download-response";
import {ProfileCaffResponse} from "./profile-caff-response";

export interface ProfileResponse {
  id: string;
  name: string;
  email: string;
  downloads: ProfileDownloadResponse[];
  caffs: ProfileCaffResponse[];
}
