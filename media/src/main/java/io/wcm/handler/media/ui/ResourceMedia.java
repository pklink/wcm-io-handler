/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.handler.media.ui;

import static io.wcm.handler.media.MediaNameConstants.PROP_CSS_CLASS;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.wcm.handler.media.Media;
import io.wcm.handler.media.MediaBuilder;
import io.wcm.handler.media.MediaHandler;

/**
 * Generic resource-based media model.
 * <p>
 * Optional use parameters when referencing model from Sightly template:
 * </p>
 * <ul>
 * <li><code>mediaFormat</code>: Media format name to restrict the allowed media items</li>
 * <li><code>refProperty</code>: Name of the property from which the media reference path is read, or node name for
 * inline media.</li>
 * <li><code>cropProperty</code>: Name of the property which contains the cropping parameters</li>
 * <li><code>rotationProperty</code>: Name of the property which contains the rotation parameter</li>
 * <li><code>cssClass</code>: CSS classes to be applied on the generated media element (most cases img element)</li>
 * </ul>
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ResourceMedia {

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private String mediaFormat;

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private String refProperty;

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private String cropProperty;

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private String rotationProperty;

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private String cssClass;

  @Self
  private MediaHandler mediaHandler;
  @SlingObject
  private Resource resource;

  private Media media;

  @PostConstruct
  private void activate() {
    MediaBuilder builder = mediaHandler.get(resource);

    if (StringUtils.isNotEmpty(mediaFormat)) {
      builder.mediaFormatName(mediaFormat);
    }
    if (StringUtils.isNotEmpty(refProperty)) {
      builder.refProperty(refProperty);
    }
    if (StringUtils.isNotEmpty(cropProperty)) {
      builder.cropProperty(cropProperty);
    }
    if (StringUtils.isNotEmpty(rotationProperty)) {
      builder.rotationProperty(rotationProperty);
    }
    if (StringUtils.isNotEmpty(cssClass)) {
      builder.property(PROP_CSS_CLASS, cssClass);
    }

    media = builder.build();
  }

  /**
   * Returns a {@link Media} object with the metadata of the resolved media.
   * Result is never null, check for validness with the {@link Media#isValid()} method.
   * @return Media
   */
  public @NotNull Media getMetadata() {
    return media;
  }

  /**
   * Returns true if the media was resolved successful.
   * @return Media is valid
   */
  public boolean isValid() {
    return media.isValid();
  }

  /**
   * Returns the XHTML markup for the resolved media object (if valid).
   * This is in most cases an img element, but may also contain other arbitrary markup.
   * @return Media markup
   */
  public @Nullable String getMarkup() {
    return media.getMarkup();
  }

}
