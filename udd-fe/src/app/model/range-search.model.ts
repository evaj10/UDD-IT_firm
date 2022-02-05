export class RangeSearch {
  constructor(
    public field: string,
    public lowerBound: number,
    public upperBound: number,
    public mustContain: string
  ) {}
}
