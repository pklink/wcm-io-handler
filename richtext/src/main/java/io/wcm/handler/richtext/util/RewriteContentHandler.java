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
package io.wcm.handler.richtext.util;

import java.util.List;

import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Allows to rewrite DOM elements and text elements.
 * <p>
 * If used for {@link io.wcm.handler.richtext.spi.RichTextHandlerConfig} this interface has to be
 * implemented by a Sling Model class. The adaptables should be
 * {@link org.apache.sling.api.SlingHttpServletRequest} and {@link org.apache.sling.api.resource.Resource}.
 * </p>
 */
@ConsumerType
public interface RewriteContentHandler {

  /**
   * Checks if the given element has to be rewritten.
   * Is called for every child single element of the parent given to rewriteContent method.
   * @param element Element to check
   * @return null if nothing is to do with this element.
   *         Return empty list to remove this element.
   *         Return list with other content to replace element with new content.
   */
  @Nullable
  List<Content> rewriteElement(@NotNull Element element);

  /**
   * Checks if the given text node has to be rewritten.
   * Is called for every text node found as parent of given to rewriteContent method.
   * @param text Text node
   * @return null if nothing is to do with this element.
   *         Return empty list to remove the element.
   *         Return list with other content to replace element with new content.
   */
  @Nullable
  List<Content> rewriteText(@NotNull Text text);

}
