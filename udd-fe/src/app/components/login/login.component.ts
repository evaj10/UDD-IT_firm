import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormGroupDirective,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthRequest } from 'src/app/model/auth-request.model';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private loginService: LoginService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    const authRequest: AuthRequest = new AuthRequest(
      this.loginForm.value.username,
      this.loginForm.value.password
    );

    this.loginService.login(authRequest).subscribe(
      (response) => {
        const jwtToken = response.accessToken;
        const expiresIn = response.expiresIn;
        sessionStorage.setItem('jwtToken', jwtToken);
        sessionStorage.setItem('expiresIn', expiresIn);
        this.router.navigate(['/hr']);
      },
      (error) => {
        this.toastr.error('Username or password incorrect');
      }
    );
  }
}
