<script setup lang="ts">
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { 
  BuildingIcon,
  BriefcaseIcon, 
  CheckIcon, 
  SaveIcon, 
  StarIcon
} from "lucide-react";
import { useState } from "react";

export default function WriteReview() {
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  
  const handleTagToggle = (tag: string) => {
    if (selectedTags.includes(tag)) {
      setSelectedTags(selectedTags.filter(t => t !== tag));
    } else {
      setSelectedTags([...selectedTags, tag]);
    }
  };
  
  return (
    <div className="grid gap-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">提交评价</h1>
      </div>
      
      <Card>
        <CardHeader>
          <CardTitle>评价兼职体验</CardTitle>
        </CardHeader>
        <CardContent className="space-y-6">
          <div className="p-4 bg-muted/50 rounded-lg flex items-center gap-4">
            <div className="h-12 w-12 rounded-lg bg-blue-100 flex items-center justify-center">
              <BuildingIcon className="h-6 w-6 text-blue-600" />
            </div>
            <div>
              <h3 className="font-medium">前端开发实习生</h3>
              <p className="text-sm text-muted-foreground">未来科技有限公司 · 2025.01-2025.03</p>
            </div>
          </div>
          
          <div className="space-y-2">
            <Label>满意度评分</Label>
            <div className="flex gap-1">
              {[1, 2, 3, 4, 5].map((star) => (
                <button
                  key={star}
                  type="button"
                  className="focus:outline-none"
                  onClick={() => setRating(star)}
                  onMouseEnter={() => setHoverRating(star)}
                  onMouseLeave={() => setHoverRating(0)}
                >
                  <StarIcon 
                    className={`h-8 w-8 ${
                      star <= (hoverRating || rating) 
                        ? "text-yellow-400 fill-yellow-400" 
                        : "text-muted-foreground"
                    }`} 
                  />
                </button>
              ))}
            </div>
            <p className="text-sm text-muted-foreground">
              {rating === 1 && "很差，不推荐"}
              {rating === 2 && "较差，有待改进"}
              {rating === 3 && "一般，符合预期"}
              {rating === 4 && "良好，有不少优点"}
              {rating === 5 && "非常好，强烈推荐"}
              {rating === 0 && "请选择您的评分"}
            </p>
          </div>
          
          <div className="space-y-2">
            <Label>评价标签（多选）</Label>
            <div className="flex flex-wrap gap-2">
              {[
                "工作环境好", "团队氛围佳", "学习收获多", "管理规范", 
                "薪资准时", "技术指导好", "工作灵活", "福利待遇好",
                "工作量合理", "晋升机会多", "压力较大", "加班较多"
              ].map((tag) => (
                <button
                  key={tag}
                  type="button"
                  onClick={() => handleTagToggle(tag)}
                  className={`rounded-full px-3 py-1 text-sm font-medium ${
                    selectedTags.includes(tag)
                      ? "bg-blue-100 text-blue-800"
                      : "bg-muted/50 text-muted-foreground hover:bg-muted"
                  }`}
                >
                  {selectedTags.includes(tag) && (
                    <CheckIcon className="h-3 w-3 inline-block mr-1" />
                  )}
                  {tag}
                </button>
              ))}
            </div>
          </div>
          
          <div className="space-y-2">
            <Label htmlFor="review">评价内容</Label>
            <Textarea 
              id="review" 
              placeholder="请描述您的兼职体验，包括工作内容、收获、建议等..."
              rows={6}
            />
            <p className="text-xs text-muted-foreground">最少10个字，最多500字</p>
          </div>
          
          <div className="space-y-2">
            <Label>匿名选项</Label>
            <div className="flex items-center gap-2">
              <input type="checkbox" id="anonymous" className="h-4 w-4" />
              <Label htmlFor="anonymous" className="text-sm font-normal">匿名发布此评价</Label>
            </div>
            <p className="text-xs text-muted-foreground">匿名评价将不会显示您的姓名和个人信息</p>
          </div>
          
          <div className="space-y-2">
            <Label>添加图片（可选）</Label>
            <div className="grid grid-cols-4 gap-2">
              <div className="aspect-square rounded border-2 border-dashed border-muted-foreground/25 flex items-center justify-center">
                <Button variant="ghost" size="icon" className="h-10 w-10 rounded-full">
                  <span className="text-2xl font-light">+</span>
                </Button>
              </div>
            </div>
            <p className="text-xs text-muted-foreground">最多上传4张图片，每张不超过5MB</p>
          </div>
          
          <div className="flex justify-end gap-2">
            <Button variant="outline">取消</Button>
            <Button className="gap-2">
              <SaveIcon className="h-4 w-4" />
              提交评价
            </Button>
          </div>
        </CardContent>
      </Card>
      
      <Card>
        <CardHeader>
          <CardTitle>待评价的工作</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            {[
              {
                id: 1,
                title: "数据分析助理",
                company: "数据智能科技",
                period: "2025.03-2025.04"
              },
              {
                id: 2,
                title: "校园推广大使",
                company: "新时代传媒",
                period: "2025.02-2025.03"
              }
            ].map((job) => (
              <div key={job.id} className="flex items-center justify-between p-3 border rounded-lg">
                <div className="flex items-center gap-3">
                  <div className="h-10 w-10 rounded-lg bg-primary/10 flex items-center justify-center">
                    <BriefcaseIcon className="h-5 w-5 text-primary" />
                  </div>
                  <div>
                    <h3 className="font-medium">{job.title}</h3>
                    <p className="text-sm text-muted-foreground">{job.company} · {job.period}</p>
                  </div>
                </div>
                <Button variant="outline" size="sm">评价</Button>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
