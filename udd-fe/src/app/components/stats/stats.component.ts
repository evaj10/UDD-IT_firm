import { Component, OnInit } from '@angular/core';
import { Stats } from 'src/app/model/stats.model';
import { StatsService } from 'src/app/services/stats.service';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css'],
})
export class StatsComponent implements OnInit {
  chosenStats: string = '';
  stats: Stats[] = [];
  constructor(private statsService: StatsService) {}

  ngOnInit(): void {}

  byCity() {
    this.chosenStats = 'city';
    this.statsService.byCity().subscribe((response) => (this.stats = response));
  }

  byDay() {
    this.chosenStats = 'day';
    this.statsService.byDay().subscribe((response) => (this.stats = response));
  }
}
