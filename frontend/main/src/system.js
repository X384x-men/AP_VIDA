System.config({
    // Existing configuration options
    map: {
      // ...
      '@angular/material': 'npm:@angular/material/bundles/material.umd.js',
  
      // CDK individual packages
      '@angular/cdk/platform': 'npm:@angular/cdk/bundles/cdk-platform.umd.js',
      '@angular/cdk/a11y': 'npm:@angular/cdk/bundles/cdk-a11y.umd.js',
      // ...
      'hammerjs': 'npm:hammerjs',
    },
    packages: {
      //...
      hammerjs: {main: './hammer.min.js', defaultExtension: 'js'}
      //...
    }
  });