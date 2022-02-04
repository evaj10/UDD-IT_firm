import { Component, Input, OnInit } from '@angular/core';
import { Result } from 'src/app/model/result.model';
import { ApplicantService } from 'src/app/services/applicant.service';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css'],
})
export class ResultComponent implements OnInit {
  @Input() result: Result = {} as Result;
  visited: boolean = false;
  constructor(private applicantService: ApplicantService) {}

  ngOnInit(): void {}

  viewCV() {
    this.visited = true;
    this.applicantService.viewApplicantCv(this.result.cvId);
  }
}
