export class AdvancedSearchField {
  constructor(
    public field: string,
    public query: string,
    public phrase: string,
    public mustContain: string
  ) {}
}
