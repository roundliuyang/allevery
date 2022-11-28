package com.yly.importannotation.zoo;

import com.yly.importannotation.animal.AnimalScanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;



@Configuration
@Import(AnimalScanConfiguration.class)
class ZooApplication {
}
