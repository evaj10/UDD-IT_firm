import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ResultPage } from 'src/app/model/result-page.model';
import { Result } from 'src/app/model/result.model';

@Component({
  selector: 'app-result-list',
  templateUrl: './result-list.component.html',
  styleUrls: ['./result-list.component.css'],
})
export class ResultListComponent implements OnInit {
  @Input() results: ResultPage = {} as ResultPage;
  @Output() pageChanged: EventEmitter<PageEvent> =
    new EventEmitter<PageEvent>();

  constructor() {}

  ngOnInit(): void {}

  onPaginateChange(event: PageEvent): void {
    this.pageChanged.emit(event);
  }
}
