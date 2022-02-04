import { AdvancedSearchField } from './advanced-search-field.model';
import { GeolocationSearch } from './geolocation-search.model';
import { RangeSearch } from './range-search.model';

export class AdvancedSearch {
  constructor(
    public fields: AdvancedSearchField[],
    public rangeRequest?: RangeSearch,
    public geoLocation?: GeolocationSearch
  ) {}
}
