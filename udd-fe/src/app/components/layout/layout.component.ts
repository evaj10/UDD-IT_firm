import { Component, OnInit } from '@angular/core';
import {
  faFileAlt,
  faSearch,
  faSearchPlus,
  faUserTie,
} from '@fortawesome/free-solid-svg-icons';
import { ApplicantService } from 'src/app/services/applicant.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
})
export class LayoutComponent implements OnInit {
  faSearch = faSearch;
  faSearchPlus = faSearchPlus;
  faUser = faUserTie;
  faStats = faFileAlt;

  currentView = 'applicant';

  constructor(private applicantService: ApplicantService) {}

  ngOnInit(): void {}

  applicant() {
    this.currentView = 'applicant';
  }

  basicSearch() {
    this.currentView = 'basicSearch';
  }

  advancedSearch() {
    this.currentView = 'advancedSearch';
  }
}
