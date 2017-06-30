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

package com.arangodb.velocypack.module.jdk8;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import com.arangodb.velocypack.VPackModule;
import com.arangodb.velocypack.VPackSetupContext;
import com.arangodb.velocypack.module.jdk8.internal.VPackJdk8Deserializers;
import com.arangodb.velocypack.module.jdk8.internal.VPackJdk8Serializers;

/**
 * @author Mark Vollmary
 *
 */
public class VPackJdk8Module implements VPackModule {

	@Override
	public <C extends VPackSetupContext<C>> void setup(final C context) {
		context.registerDeserializer(Instant.class, VPackJdk8Deserializers.INSTANT);
		context.registerDeserializer(LocalDate.class, VPackJdk8Deserializers.LOCAL_DATE);
		context.registerDeserializer(LocalDateTime.class, VPackJdk8Deserializers.LOCAL_DATE_TIME);
		context.registerDeserializer(Optional.class, VPackJdk8Deserializers.OPTIONAL, true);
		context.registerDeserializer(OptionalDouble.class, VPackJdk8Deserializers.OPTIONAL_DOUBLE, true);
		context.registerDeserializer(OptionalInt.class, VPackJdk8Deserializers.OPTIONAL_INT, true);
		context.registerDeserializer(OptionalLong.class, VPackJdk8Deserializers.OPTIONAL_LONG, true);

		context.registerSerializer(Instant.class, VPackJdk8Serializers.INSTANT);
		context.registerSerializer(LocalDate.class, VPackJdk8Serializers.LOCAL_DATE);
		context.registerSerializer(LocalDateTime.class, VPackJdk8Serializers.LOCAL_DATE_TIME);
		context.registerSerializer(Optional.class, VPackJdk8Serializers.OPTIONAL);
		context.registerSerializer(OptionalDouble.class, VPackJdk8Serializers.OPTIONAL_DOUBLE);
		context.registerSerializer(OptionalInt.class, VPackJdk8Serializers.OPTIONAL_INT);
		context.registerSerializer(OptionalLong.class, VPackJdk8Serializers.OPTIONAL_LONG);
	}

}
