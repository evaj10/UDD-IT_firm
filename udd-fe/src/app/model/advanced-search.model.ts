import { AdvancedSearchField } from './advanced-search-field.model';
import { GeolocationSearch } from './geolocation-search.model';

export class AdvancedSearch {
  constructor(
    public fields: AdvancedSearchField[],
    public geoLocation: GeolocationSearch
  ) {}
}
