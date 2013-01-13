/*************************************************************************
 * Copyright 2009-2012 Eucalyptus Systems, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Please contact Eucalyptus Systems, Inc., 6755 Hollister Ave., Goleta
 * CA 93117, USA or visit http://www.eucalyptus.com/licenses/ if you need
 * additional information or have any questions.
 ************************************************************************/
package com.eucalyptus.tags;

import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * Methods for working with {@link Filter}s
 */
public class Filters {

  /**
   * Generate a Filter for the given filters.
   *
   * @param filters The filter items
   * @param resourceType The resource class to be filtered
   * @return The filter
   * @throws InvalidFilterException If a filter is invalid
   */
  @Nonnull
  public static Filter generate( final Iterable<edu.ucsb.eucalyptus.msgs.Filter> filters,
                                 final Class<?> resourceType ) throws InvalidFilterException {
    final Filter filter;

    final FilterSupport support = FilterSupport.forResource( resourceType );
    if ( support == null ) {
      filter = Filter.alwaysTrue();
    } else {
      filter = support.generate( toMap( filters ) );
    }

    return filter;
  }

  private static Map<String, Set<String>> toMap( final Iterable<edu.ucsb.eucalyptus.msgs.Filter> filters ) {
    final ImmutableMap.Builder<String,Set<String>> filterMapBuilder = ImmutableMap.builder();

    for ( final edu.ucsb.eucalyptus.msgs.Filter filter : filters ) {
      final Set<String> values = ImmutableSet.copyOf(
          Iterables.transform( filter.getValueSet(), NullToEmptyString.INSTANCE ) );
      filterMapBuilder.put( filter.getName(), values );
    }

    return filterMapBuilder.build();
  }

  private enum NullToEmptyString implements Function<String,String> {
    INSTANCE;

    @Override
    public String apply( final String text ) {
      return text == null ? "" : text;
    }
  }
}
