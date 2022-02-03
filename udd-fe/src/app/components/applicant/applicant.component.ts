import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApplicantService } from 'src/app/services/applicant.service';

@Component({
  selector: 'app-applicant',
  templateUrl: './applicant.component.html',
  styleUrls: ['./applicant.component.css'],
})
export class ApplicantComponent implements OnInit {
  applicantForm: FormGroup;
  fileName: string = '';
  fileType: string = '';
  file: any = null;

  constructor(private applicantService: ApplicantService) {
    this.applicantForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      education: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

  submitApplication() {
    if (this.applicantForm.invalid || this.fileType !== 'pdf') {
      return;
    }

    var formData: any = new FormData();
    formData.append('name', this.applicantForm.value.name);
    formData.append('surname', this.applicantForm.value.surname);
    formData.append('email', this.applicantForm.value.email);
    formData.append('address', this.applicantForm.value.address);
    formData.append('educationLevelId', this.applicantForm.value.education);
    formData.append('cv', this.file);

    this.applicantService
      .apply(formData)
      .subscribe((response) => console.log(response));

    // this.applicantService.apply(userLoginDto).subscribe(
    //   (response) => {
    //     this.toastr.success('Logged in successfully!');
    //     this.authService.setLoggedInUser(response);
    //     this.loginForm.reset();
    //     this.router.navigate(['shopping']);
    //     this.authService.role.next(this.authService.getLoggedInUserAuthority());
    //   },
    //   (error) => {
    //     this.toastr.error('Incorrect email or password.');
    //     this.loginForm.reset();
    //   }
    // );
  }

  displayFileName(event: any) {
    this.file = event.target.files[0];
    this.fileName = event.target.files[0].name;
    let lastDot = this.fileName.lastIndexOf('.');
    this.fileType = this.fileName.substring(lastDot + 1);
  }

  getFieldErrorMessage(fieldName: string): string {
    if (this.applicantForm.controls[fieldName].touched) {
      if (this.applicantForm.controls[fieldName].hasError('required')) {
        return 'Required field';
      }
      if (this.applicantForm.controls[fieldName].hasError('email')) {
        return 'Invalid email format';
      }
    }

    return '';
  }
}
