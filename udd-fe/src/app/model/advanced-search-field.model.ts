export class AdvancedSearchField {
  constructor(
    public query: string,
    public field: string,
    public phrase: boolean,
    public mustContain: boolean
  ) {}
}
