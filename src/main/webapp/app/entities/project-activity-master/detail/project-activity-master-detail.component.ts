import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectActivityMaster } from '../project-activity-master.model';

@Component({
  selector: 'jhi-project-activity-master-detail',
  templateUrl: './project-activity-master-detail.component.html',
})
export class ProjectActivityMasterDetailComponent implements OnInit {
  projectActivityMaster: IProjectActivityMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectActivityMaster }) => {
      this.projectActivityMaster = projectActivityMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
