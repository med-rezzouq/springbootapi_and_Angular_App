import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzButtonSize } from 'ng-zorro-antd/button';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UserService } from '../../user-services/user.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.scss']
})
export class MyOrdersComponent implements OnInit {

  validateForm!: FormGroup;
  isSpinning = false;
  size: NzButtonSize = 'large';
  MyOrders: any

  constructor(private userService: UserService,
    private notification: NzNotificationService,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
    });
    this.getMyOrdersByUserId();
  }

  getMyOrdersByUserId() {
    this.userService.getMyOrdersByUserId().subscribe((res) => {
      this.MyOrders = res;
    })
  }

}

