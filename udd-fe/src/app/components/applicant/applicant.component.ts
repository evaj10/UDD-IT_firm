import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormGroupDirective,
  Validators,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ApplicantService } from 'src/app/services/applicant.service';

@Component({
  selector: 'app-applicant',
  templateUrl: './applicant.component.html',
  styleUrls: ['./applicant.component.css'],
})
export class ApplicantComponent implements OnInit {
  applicantForm: FormGroup;
  @ViewChild('addressInput') addressInput: ElementRef = {} as ElementRef;
  fileName: string = '';
  fileType: string = '';
  file: any = null;

  constructor(
    private applicantService: ApplicantService,
    private toastr: ToastrService
  ) {
    this.applicantForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      education: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

  submitApplication(searchFormDirective: FormGroupDirective) {
    if (this.applicantForm.invalid || this.fileType !== 'pdf') {
      return;
    }

    let formData: any = new FormData();
    formData.append('name', this.applicantForm.value.name);
    formData.append('surname', this.applicantForm.value.surname);
    formData.append('email', this.applicantForm.value.email);
    formData.append('address', this.applicantForm.value.address);
    formData.append('educationLevelId', this.applicantForm.value.education);
    formData.append('cv', this.file);

    this.applicantService.getIpAddress().subscribe((res: any) => {
      const ipAddress = res.ip;
      formData.append('ipAddress', ipAddress);
      this.applicantService.apply(formData).subscribe(
        (response) => {
          this.applicantForm.reset();
          searchFormDirective.resetForm();
          this.file = null;
          this.fileName = '';
          this.fileType = '';
          this.toastr.success('Succesfully applied for the job');
        },
        (error) => {
          const errorJson = JSON.parse(error.error);
          this.toastr.error(errorJson.message);
          this.addressInput.nativeElement.focus();
        }
      );
    });
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
