import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NzButtonSize } from 'ng-zorro-antd/button';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UserService } from '../../user-services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  CartProducts: any = [];
  totalAmount: any;
  quantity: any;
  validateForm!: FormGroup;
  isSpinning = false;
  size: NzButtonSize = 'large';

  constructor(private userService: UserService,
    private notification: NzNotificationService,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit(): void {
    this.getCart();
    this.validateForm = this.fb.group({
    });
  }

  getCart() {
    this.isSpinning = true;
    this.userService.getCartByUserId().subscribe((res) => {
      this.CartProducts = res.cartItems;
      this.totalAmount = res.amount;
      this.quantity = res.cartItems.quantity;
    });
  }

  minusProduct(productId: any) {
    this.isSpinning = true;
    this.userService.addMinusOnProduct(productId).subscribe((res) => {
      this.notification
        .success(
          'SUCCÈS',
          `Quantité mise à jour`,
          { nzDuration: 5000 }
        );
      this.getCart();
    })
  }

  plusProduct(productId: any) {
    this.isSpinning = true;
    this.userService.addPlusOnProduct(productId).subscribe((res) => {
      this.notification
        .success(
          'SUCCÈS',
          `Quantité mise à jour`,
          { nzDuration: 5000 }
        );
      this.getCart();
    })
  }

  placeOrder() {
    this.isSpinning = true;
    this.userService.placeOrder(this.validateForm.value).subscribe((res) => {
      this.notification
        .success(
          'SUCCÈS',
          `Commande passée avec succès!`,
          { nzDuration: 5000 }
        );
      this.isSpinning = false
      this.router.navigateByUrl("/user/my-orders")
    });
  }

}
