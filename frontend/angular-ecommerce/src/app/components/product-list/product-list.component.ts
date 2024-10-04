import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../common/product';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  products:Product[] = [];
  currentCategoryId:number = 1; // keep track of current category number
  
  constructor(private productService:ProductService,
              private route:ActivatedRoute) {} // initialize the current active route that loaded the component

  // Subscribe the actived route to the `listProducts()` function
  ngOnInit():void {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts() {

    // Check if `id` parameter is available
    const hasCategoryId:boolean = this.route.snapshot.paramMap.has('id');

    if (hasCategoryId) {
      // Get the `id` parameter string. Convert string to a number using the `+` symbol
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!; // in this context, `!` is the non-null assertion operator
    } else {
      // No category id available, default to category 1
      this.currentCategoryId = 1;
    }
    

    this.productService.getProductList(this.currentCategoryId)
      .subscribe( data => { this.products = data; } );
  }
}