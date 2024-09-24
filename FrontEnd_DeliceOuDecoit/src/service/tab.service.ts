import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TabService {

  private currentTab: string = 'avis'; // Default tab

  setCurrentTab(tab: string) {
    this.currentTab = tab;
  }

  getCurrentTab() {
    return this.currentTab;
  }
}
