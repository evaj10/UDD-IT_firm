import { Component, Input, OnInit } from '@angular/core';
import { Result } from 'src/app/model/result.model';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css'],
})
export class ResultComponent implements OnInit {
  @Input() result: Result = {} as Result;
  constructor() {}

  ngOnInit(): void {}
}
