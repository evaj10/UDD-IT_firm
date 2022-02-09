import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  faDoorOpen,
  faExpandArrowsAlt,
  faFileAlt,
  faRunning,
  faSearch,
  faSearchPlus,
  faUserTie,
} from '@fortawesome/free-solid-svg-icons';

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
  faLogout = faDoorOpen;

  currentView = 'basicSearch';

  constructor(private router: Router) {}

  ngOnInit(): void {}

  // applicant() {
  //   this.currentView = 'applicant';
  // }

  basicSearch() {
    this.currentView = 'basicSearch';
  }

  advancedSearch() {
    this.currentView = 'advancedSearch';
  }

  stats() {
    this.currentView = 'stats';
  }

  logout() {
    sessionStorage.removeItem('jwtToken');
    sessionStorage.removeItem('expiresIn');
    this.router.navigate(['/login']);
  }
}
