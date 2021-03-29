# CoordinatorLayout 协调布局的基本使用

## AppBarLayout_Layout AppBarLayout子View的滚动属性layout_scrollFlags
    <attr name="layout_scrollFlags">
      <!-- Disable scrolling on the view. This flag should not be combined with any of the other
           scroll flags. -->
      <flag name="noScroll" value="0x0"/>

      <!-- The view will be scroll in direct relation to scroll events. This flag needs to be
           set for any of the other flags to take effect. If any sibling views
           before this one do not have this flag, then this value has no effect. -->
           所有想滚动出屏幕的view都需要设置这个flag， 没有设置这个flag的view将被固定在屏幕顶部
      <flag name="scroll" value="0x1"/>

      <!-- When exiting (scrolling off screen) the view will be scrolled until it is
           'collapsed'. The collapsed height is defined by the view's minimum height. -->
           当给AppBarLayout设置app:layout_scrollFlags=“scroll|exitUntilCollapsed”，同时顶部栏的Toolbar设置minHeight属性时。当RecyclerView向上滚动至顶部栏的最小高度后，AppBarLayout不在收缩。下拉时，RecyclerView没有到达最顶部时，只显示顶部栏最小高度。
      <flag name="exitUntilCollapsed" value="0x2"/>

      <!-- When entering (scrolling on screen) the view will scroll on any downwards
           scroll event, regardless of whether the scrolling view is also scrolling. This
           is commonly referred to as the 'quick return' pattern. -->
           AppBarLayout在下拉时直接显示
      <flag name="enterAlways" value="0x4"/>

      <!-- An additional flag for 'enterAlways' which modifies the returning view to
           only initially scroll back to it's collapsed height. Once the scrolling view has
           reached the end of it's scroll range, the remainder of this view will be scrolled
           into view. -->
           当给AppBarLayout设置app:layout_scrollFlags=“scroll|enterAlways|enterAlwaysCollapsed”，同时顶部栏的Toolbar设置minHeight属性时，下拉后会先显示最小高度，到顶部时显示完全。（enterAlwaysCollapsed一般是配合enterAlways一起使用的，同时一定要给Toolbar设置最小高度(minheight)，不过Toolbar默认最小高度就是ActionBar的高度…）
      <flag name="enterAlwaysCollapsed" value="0x8"/>

      <!-- Upon a scroll ending, if the view is only partially visible then it will be
           snapped and scrolled to it's closest edge. -->
           当一个滚动事件结束，如果视图是部分可见的，那么它将被滚动到收缩或展开。例如，如果视图只有底部25%显示，它将折叠。相反，如果它的底部75%可见，那么它将完全展开。
      <flag name="snap" value="0x10"/>

      <!-- An additional flag to be used with 'snap'. If set, the view will be snapped to its
           top and bottom margins, as opposed to the edges of the view itself. -->
      <flag name="snapMargins" value="0x20"/>
    </attr>
## CoordinatorLayout_Layout  CoordinatorLayout子View的滚动行为layout_behavior