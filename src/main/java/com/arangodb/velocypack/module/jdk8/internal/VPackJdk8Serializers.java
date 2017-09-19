/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.velocypack.module.jdk8.internal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import com.arangodb.velocypack.VPackSerializer;
import com.arangodb.velocypack.internal.VPackSerializers;

/**
 * @author Mark Vollmary
 *
 */
public class VPackJdk8Serializers {

	public static final VPackSerializer<Instant> INSTANT = (builder, attribute, value, context) -> {
		VPackSerializers.DATE.serialize(builder, attribute, Date.from(value), context);
	};
	public static final VPackSerializer<LocalDate> LOCAL_DATE = (builder, attribute, value, context) -> {
		INSTANT.serialize(builder, attribute, value.atStartOfDay(ZoneId.systemDefault()).toInstant(), context);
	};
	public static final VPackSerializer<LocalDateTime> LOCAL_DATE_TIME = (builder, attribute, value, context) -> {
		INSTANT.serialize(builder, attribute, value.atZone(ZoneId.systemDefault()).toInstant(), context);
	};
	public static final VPackSerializer<ZonedDateTime> ZONED_DATE_TIME = (builder, attribute, value, context) -> {
		INSTANT.serialize(builder, attribute, value.toInstant(), context);
	};
	public static final VPackSerializer<OffsetDateTime> OFFSET_DATE_TIME = (builder, attribute, value, context) -> {
		INSTANT.serialize(builder, attribute, value.toInstant(), context);
	};
	public static final VPackSerializer<ZoneId> ZONE_ID = (builder, attribute, value, context) -> {
		builder.add(attribute, value.getId());
	};
	public static final VPackSerializer<Optional<?>> OPTIONAL = (builder, attribute, value, context) -> {
		context.serialize(builder, attribute, value.orElse(null));
	};
	public static final VPackSerializer<OptionalDouble> OPTIONAL_DOUBLE = (builder, attribute, value, context) -> {
		context.serialize(builder, attribute, value.isPresent() ? value.getAsDouble() : null);
	};
	public static final VPackSerializer<OptionalInt> OPTIONAL_INT = (builder, attribute, value, context) -> {
		context.serialize(builder, attribute, value.isPresent() ? value.getAsInt() : null);
	};
	public static final VPackSerializer<OptionalLong> OPTIONAL_LONG = (builder, attribute, value, context) -> {
		context.serialize(builder, attribute, value.isPresent() ? value.getAsLong() : null);
	};

}
