export class AdvancedSearchField {
  constructor(
    public field: string,
    public query: string,
    public phrase: boolean,
    public mustContain: boolean
  ) {}
}
