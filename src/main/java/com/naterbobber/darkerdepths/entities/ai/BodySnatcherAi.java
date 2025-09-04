package com.naterbobber.darkerdepths.entities.ai;

public class BodySnatcherAi {
/*
    // --- AI Constants ---
    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.4F;
    private static final float SPEED_MULTIPLIER_WHEN_FIGHTING = 1.1F;
    private static final int MELEE_ATTACK_COOLDOWN = 20; // Ticks
    private static final int DASH_COOLDOWN_TICKS = 100; // 5 seconds
    private static final double DASH_TRIGGER_DISTANCE_SQUARED = 100.0D; // 10 blocks squared for efficiency
    private static final double DASH_SPEED = 1.8D; // How fast the dash push is

    // --- Sensors and Memories ---
    private static final List<SensorType<? extends Sensor<? super BodySnatcherEntity>>> SENSOR_TYPES = List.of(
            SensorType.NEAREST_PLAYERS
    );

    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            DDMemoryModuleTypes.DASH_COOLDOWN.get() // Our custom memory
    );

    public static Brain.Provider<BodySnatcherEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    public static Brain<BodySnatcherEntity> makeBrain(Brain<BodySnatcherEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        initDashActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    // --- Activity Management (FIXED) ---
    public static void updateActivity(BodySnatcherEntity entity) {
        // This method now contains the core logic for deciding whether to FIGHT or DASH.
        Brain<BodySnatcherEntity> brain = entity.getBrain();
        Optional<LivingEntity> targetOptional = brain.getMemory(MemoryModuleType.ATTACK_TARGET);

        // If there's no target, go to IDLE.
        if (targetOptional.isEmpty()) {
            brain.setActiveActivityIfPossible(Activity.IDLE);
            return;
        }

        LivingEntity target = targetOptional.get();
        double distanceSqr = entity.distanceToSqr(target);

        // Condition to DASH: target is far away AND the dash ability is not on cooldown.
        if (distanceSqr >= DASH_TRIGGER_DISTANCE_SQUARED && !brain.hasMemoryValue(DDMemoryModuleTypes.DASH_COOLDOWN.get())) {
            brain.setActiveActivityIfPossible(DDActivities.DASH.get());
        } else {
            // Otherwise, engage in normal fighting (chasing and melee attacks).
            brain.setActiveActivityIfPossible(Activity.FIGHT);
        }
    }


    // --- Activity Definitions (FIXED) ---

    private static void initCoreActivity(Brain<BodySnatcherEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));
    }

    private static void initIdleActivity(Brain<BodySnatcherEntity> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                StartAttacking.create(e -> e.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)),
                new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.stroll(SPEED_MULTIPLIER_WHEN_IDLING), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                ))
        ));
    }

    private static void initFightActivity(Brain<BodySnatcherEntity> brain) {
        // FIX: This now uses the correct method signature. It simply requires an attack target to run.
        // The decision of *when* to run it is now handled by updateActivity.
        brain.addActivityWithConditions(Activity.FIGHT,
                ImmutableList.of(
                        Pair.of(0, StopAttackingIfTargetInvalid.create()),
                        Pair.of(1, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(SPEED_MULTIPLIER_WHEN_FIGHTING)),
                        Pair.of(2, MeleeAttack.create(MELEE_ATTACK_COOLDOWN))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static void initDashActivity(Brain<BodySnatcherEntity> brain) {
        // FIX: This now uses the correct method signature.
        brain.addActivityWithConditions(DDActivities.DASH.get(),
                ImmutableList.of(
                        Pair.of(0, createDashBehavior())
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
                        Pair.of(DDMemoryModuleTypes.DASH_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT)
                )
        );
    }


    // --- Custom Behavior for Dashing (No changes needed here) ---
    private static BehaviorControl<BodySnatcherEntity> createDashBehavior() {
        return BehaviorBuilder.create(instance -> instance.group(
                instance.present(MemoryModuleType.ATTACK_TARGET),
                instance.absent(DDMemoryModuleTypes.DASH_COOLDOWN.get())
        ).apply(instance, (attackTarget, dashCooldown) -> (level, entity, gameTime) -> {
            LivingEntity target = instance.get(attackTarget);
            if (target == null) {
                return false;
            }
            Vec3 direction = entity.position().vectorTo(target.position()).normalize();
            entity.setDeltaMovement(direction.scale(DASH_SPEED));
            dashCooldown.setWithExpiry(Unit.INSTANCE, DASH_COOLDOWN_TICKS);
            return true;
        }));
    }

 */
}