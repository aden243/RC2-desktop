/*
 * Copyright (c) 2016-2022 University of Washington
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *  Neither the name of the University of Washington nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY OF WASHINGTON AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY OF WASHINGTON OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package edu.uw.cse.ifrcdemo.healthplan.data;

import edu.uw.cse.ifrcdemo.healthplan.model.DonorRepository;
import edu.uw.cse.ifrcdemo.healthplan.model.HealthServiceRepository;
import edu.uw.cse.ifrcdemo.healthplan.model.HealthServicesForTaskRespository;
import edu.uw.cse.ifrcdemo.healthplan.model.HealthTaskRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.authorization.AuthorizationRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.csv.CsvRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.distribution.DistributionRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.entitlement.EntitlementRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.item.ItemRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.location.LocationRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.rctemplate.RcTemplateRepository;
import edu.uw.cse.ifrcdemo.sharedlib.model.config.AuxiliaryProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class DataInstanceConfiguration {
  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public HealthDataRepos dataRepos() {
    return HealthDataInstance.getDataRepos();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public CsvRepository csvRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getCsvRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AuxiliaryProperty auxiliaryProperty(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getAuxiliaryProperty();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AuthorizationRepository authorizationRepository(HealthDataRepos reliefDataRepos) {
    return reliefDataRepos.getAuthorizationRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public DistributionRepository distributionRepository(HealthDataRepos reliefDataRepos) {
    return reliefDataRepos.getDistributionRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public EntitlementRepository entitlementRepository(HealthDataRepos reliefDataRepos) {
    return reliefDataRepos.getEntitlementRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public RcTemplateRepository rcTemplateRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getRcTemplateRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public LocationRepository regionRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getLocationRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ItemRepository itemPackRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getItemRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public HealthServiceRepository healthServiceRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getHealthServiceRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public DonorRepository donorRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getDonorRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public HealthTaskRepository healthTaskRepository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getHealthTaskRepository();
  }

  @Bean
  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public HealthServicesForTaskRespository healthServicesForTaskRespository(HealthDataRepos healthDataRepos) {
    return healthDataRepos.getHealthServicesForTaskRespository();
  }
}
