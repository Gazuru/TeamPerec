export interface JwtResponse {
  token: string;
  type: string;
  id: string;
  username: string;
  email: string;
  roles: string[];
}
