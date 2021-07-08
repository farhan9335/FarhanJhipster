import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityMaster } from '../activity-master.model';

@Component({
  selector: 'jhi-activity-master-detail',
  templateUrl: './activity-master-detail.component.html',
})
export class ActivityMasterDetailComponent implements OnInit {
  activityMaster: IActivityMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityMaster }) => {
      this.activityMaster = activityMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
