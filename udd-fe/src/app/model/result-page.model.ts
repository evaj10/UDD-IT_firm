import { Result } from './result.model';

export class ResultPage {
  constructor(public content: Result[], public totalElements: number) {}
}
