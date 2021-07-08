import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChangeRequestMaster } from '../change-request-master.model';

@Component({
  selector: 'jhi-change-request-master-detail',
  templateUrl: './change-request-master-detail.component.html',
})
export class ChangeRequestMasterDetailComponent implements OnInit {
  changeRequestMaster: IChangeRequestMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ changeRequestMaster }) => {
      this.changeRequestMaster = changeRequestMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
