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

package edu.uw.cse.ifrcdemo.distplan.ui.mainmenu;

import edu.uw.cse.ifrcdemo.distplan.data.ReliefDataInstance;
import edu.uw.cse.ifrcdemo.distplan.data.ReliefDataRepos;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainMenuController {
  private static final String MAIN_MENU_TEMPLATE = "mainmenu/mainMenu";

  private final Logger logger;

  public MainMenuController(Logger logger) {
    this.logger = logger;
  }

  @GetMapping("")
  public ModelAndView mainMenu() {
    ModelAndView modelAndView = new ModelAndView(MAIN_MENU_TEMPLATE);
    ReliefDataRepos reliefDataRepos = ReliefDataInstance.getDataRepos();
    if (reliefDataRepos == null) {
      modelAndView.addObject("disableButtons", true);
    } else {
      modelAndView.addObject("disableButtons", false);
    }

    return modelAndView;
  }
}
